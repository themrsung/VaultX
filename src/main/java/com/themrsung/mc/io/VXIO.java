package com.themrsung.mc.io;

import com.themrsung.mc.economy.*;
import com.themrsung.mc.economy.property.*;
import com.themrsung.mc.util.Coordinate;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * VaultX Input/Output Utilities
 */
public abstract class VXIO {
    /**
     * Serializes the provided account holder if it is a VaultX internal type. If VaultX does not know
     * how to serialize the holder, this will return an empty list.
     *
     * @param holder The account holder to serialize
     * @return The serialized lines of strings
     */
    public static @NotNull List<String> serializeAccountHolder(VXAccountHolder holder) {
        var result = new ArrayList<String>();

        // VaultX does not know how to serialize a non-abstract-holder implementation.
        if (!(holder instanceof VXAbstractAccountHolder vxaah)) return List.of();

        result.add("type: " + vxaah.getType()); // Type comes first for easy deserialization
        result.add("uniqueId: " + vxaah.getUniqueId());

        var name = vxaah.getName();
        result.add("name: " + Objects.requireNonNullElse(name, ""));

        result.add("balance: " + vxaah.getBalance());
        result.add("premiumBalance: " + vxaah.getPremiumBalance());

        if (holder instanceof VXInstitution vxi) {
            result.add("owner: " + vxi.getOwner().getUniqueId());
            result.add("members: ");
            result.addAll(indentText(
                    vxi.getMembers()
                            .map(OfflinePlayer::getUniqueId)
                            .map(UUID::toString)
                            .toList()
            ));
        }

        var properties = serializeAccountHolderProperties(vxaah.getProperties());
        var indentedProperties = indentText(properties);

        result.add("properties:");
        result.addAll(indentedProperties);

        return List.copyOf(result);
    }

    /**
     * Serializes the given stream of account holder properties.
     *
     * @param properties The properties to serialize
     * @return The serialized lines of strings
     */
    public static @NotNull List<String> serializeAccountHolderProperties(Stream<AccountHolderProperty<?>> properties) {
        var result = new ArrayList<String>();
        var i = new int[1];

        properties.forEach(p -> {
            var index = i[0]++;
            var serialized = serializeAccountHolderProperty(p);
            var indented = indentText(serialized);

            result.add(index + ":");
            result.addAll(indented);
        });

        return List.copyOf(result);
    }

    /**
     * Serializes the given account holder property.
     *
     * @param p The property to serialize
     * @return The serialized lines of strings
     */
    public static @NotNull List<String> serializeAccountHolderProperty(AccountHolderProperty<?> p) {
        var result = new ArrayList<String>();

        result.add("type: " + p.getType()); // Type must come first to make deserialization easier
        result.add("name: " + p.getName());

        switch (p.getType()) {
            case COORDINATE, DOUBLE, LONG, STRING, BOOLEAN, UUID -> result.add("value: " + p.getValue().toString());

            case DATE -> {
                var dp = (DateProperty) p;
                result.add("value: " + (dp.getValue() != null ? dp.getValue().getTime() : -1));
            }

            case PLAYER -> {
                var pl = (PlayerProperty) p;
                var op = pl.getValue();
                result.add("value: " + (op != null ? op.getUniqueId() : "null"));
            }

            case HOLDER -> {
                var hp = (HolderProperty) p;
                var ah = hp.getValue();
                result.add("value: " + (ah != null ? ah.getUniqueId() : "null"));
            }

            case STRING_ARRAY, COORDINATE_ARRAY, UUID_ARRAY, DOUBLE_ARRAY, LONG_ARRAY, BOOLEAN_ARRAY ->
                    result.add("value: " + Arrays.toString(Objects.requireNonNullElse(((Object[]) p.getValue()), new Object[0])));

            case DATE_ARRAY -> {
                var da = (DateArrayProperty) p;
                result.add("value: " + Arrays.toString(
                        Arrays.stream(da.getValue())
                                .mapToLong(d -> d != null ? d.getTime() : -1)
                                .toArray())
                );
            }

            case PLAYER_ARRAY -> {
                var pa = (PlayerArrayProperty) p;
                result.add("value: " + Arrays.toString(
                        Arrays.stream(pa.getValue())
                                .map(pl -> pl != null ? pl.getUniqueId() : null)
                                .map(uuid -> uuid != null ? uuid.toString() : "null")
                                .toArray(String[]::new))
                );
            }

            case HOLDER_ARRAY -> {
                var ha = (HolderArrayProperty) p;
                result.add("value: " + Arrays.toString(
                        Arrays.stream(ha.getValue())
                                .map(h -> h != null ? h.getUniqueId() : null)
                                .map(uuid -> uuid != null ? uuid.toString() : "null")
                                .toArray(String[]::new)
                ));
            }
        }

        return List.copyOf(result);
    }

    /**
     * Deserializes an account holder property.
     *
     * @param lines The lines to deserialize
     * @return The deserialized object
     * @throws IOException When deserialization fails
     */
    public static @NotNull AccountHolderProperty<?> deserializeAccountHolderProperty(List<String> lines) throws IOException {
        var remaining = new ArrayList<>(lines);
        var typeSplit = remaining.getFirst().split(": ");
        if (!Objects.equals(typeSplit[0].trim(), "type")) throw new IOException("Cannot infer type of property.");

        var typeAsString = typeSplit[1].trim();
        var type = AccountHolderPropertyType.valueOf(typeAsString);

        remaining.removeFirst();

        var nameSplit = remaining.getFirst().split(": ");
        if (!Objects.equals(nameSplit[0].trim(), "name")) throw new IOException("Cannot read name of property.");

        var name = nameSplit[1];

        remaining.removeFirst();

        var valueSplit = remaining.getFirst().split(": ");
        if (!Objects.equals(valueSplit[0].trim(), "value")) throw new IOException("Cannot read value of property");


        var valueAsString = tryCatchOrElse(() -> valueSplit[1], "");
        var trimmedValue = valueAsString.substring(1, valueAsString.length() - 1);

        switch (type) {
            case STRING -> {
                return AccountHolderProperty.newString(name, valueAsString);
            }

            case STRING_ARRAY -> {
                return AccountHolderProperty.newStringArray(name, trimmedValue.split(", "));
            }

            case COORDINATE -> {
                return AccountHolderProperty.newCoordinate(name, Coordinate.parseCoordinate(valueAsString));
            }

            case COORDINATE_ARRAY -> {
                return AccountHolderProperty.newCoordinateArray(name, Arrays.stream(trimmedValue.split("}, "))
                        .map(s -> {
                            try {
                                return Coordinate.parseCoordinate(s);
                            } catch (IllegalArgumentException e) {
                                return null;
                            }
                        })
                        .toArray(Coordinate[]::new));
            }

            case PLAYER -> {
                return AccountHolderProperty.newPlayer(name, parsePlayer(valueAsString));
            }

            case PLAYER_ARRAY -> {
                return AccountHolderProperty.newPlayerArray(name, Arrays.stream(trimmedValue.split(", "))
                        .map(VXIO::parsePlayer)
                        .toArray(OfflinePlayer[]::new));
            }

            case HOLDER -> {
                return AccountHolderProperty.newHolder(name, parseHolder(valueAsString));
            }

            case HOLDER_ARRAY -> {
                return AccountHolderProperty.newHolderArray(name, Arrays.stream(trimmedValue.split(", "))
                        .map(VXIO::parseHolder)
                        .toArray(VXAccountHolder[]::new));
            }

            case DATE -> {
                try {
                    long timestamp = Long.parseLong(valueAsString);
                    return AccountHolderProperty.newDate(name, new Date(timestamp));
                } catch (NumberFormatException e) {
                    throw new IOException("Invalid date format");
                }
            }

            case DATE_ARRAY -> {
                return AccountHolderProperty.newDateArray(name, Arrays.stream(trimmedValue.split(", "))
                        .map(s -> {
                            try {
                                return new Date(Long.parseLong(s));
                            } catch (Throwable e) {
                                return null;
                            }
                        }).toArray(Date[]::new));
            }

            case UUID -> {
                try {
                    var uuid = UUID.fromString(valueAsString);
                    return AccountHolderProperty.newUniqueId(name, uuid);
                } catch (IllegalArgumentException e) {
                    throw new IOException("Invalid UUID format");
                }
            }

            case UUID_ARRAY -> {
                return AccountHolderProperty.newUniqueIdArray(name, Arrays.stream(trimmedValue.split(", "))
                        .map(s -> {
                            try {
                                return UUID.fromString(s);
                            } catch (Throwable e) {
                                return null;
                            }
                        }).toArray(UUID[]::new));
            }

            case DOUBLE -> {
                try {
                    double value = Double.parseDouble(valueAsString);
                    return AccountHolderProperty.newDouble(name, value);
                } catch (NumberFormatException e) {
                    throw new IOException("Invalid double format");
                }
            }

            case DOUBLE_ARRAY -> {
                return AccountHolderProperty.newDoubleArray(name, Arrays.stream(valueAsString.split(", "))
                        .mapToDouble(s -> {
                            try {
                                return Double.parseDouble(s);
                            } catch (NumberFormatException e) {
                                return 0;
                            }
                        }).toArray());
            }

            case LONG -> {
                try {
                    long value = Long.parseLong(valueAsString);
                    return AccountHolderProperty.newLong(name, value);
                } catch (NumberFormatException e) {
                    throw new IOException("Invalid long format");
                }
            }

            case LONG_ARRAY -> {
                return AccountHolderProperty.newLongArray(name, Arrays.stream(valueAsString.split(", "))
                        .mapToLong(s -> {
                            try {
                                return Long.parseLong(s);
                            } catch (NumberFormatException e) {
                                return 0;
                            }
                        }).toArray());
            }

            case BOOLEAN -> {
                boolean value = Boolean.parseBoolean(valueAsString);
                return AccountHolderProperty.newBoolean(name, value);
            }

            case BOOLEAN_ARRAY -> {
                var boxedArray = Arrays.stream(valueAsString.split(", "))
                        .map(Boolean::parseBoolean).toArray(Boolean[]::new);
                var primArray = new boolean[boxedArray.length];

                for (int i = 0; i < boxedArray.length; i++) {
                    primArray[i] = boxedArray[i];
                }

                return AccountHolderProperty.newBooleanArray(name, primArray);
            }
        }

        throw new IOException("Error deserializing: " + name);
    }

    private static <T> T parseValue(String s, Function<String, T> parser) throws IOException {
        try {
            return parser.apply(s);
        } catch (Throwable e) {
            throw new IOException("Invalid format", e);
        }
    }

    private static @Nullable OfflinePlayer parsePlayer(String s) {
        try {
            var uuid = UUID.fromString(s);
            return Bukkit.getOfflinePlayer(uuid);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    private static @Nullable VXAccountHolder parseHolder(String s) {
        try {
            var uuid = UUID.fromString(s);
            return new VXAccountHolderReference(uuid);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    /**
     * Deserializes the given account holder properties.
     *
     * @param lines The lines to deserialize
     * @return The deserialized list
     * @throws IOException When IO fails
     */
    public static @NotNull List<AccountHolderProperty<?>> deserializeAccountHolderProperties(List<String> lines) throws IOException {
        var result = new ArrayList<AccountHolderProperty<?>>();

        int i = 0;
        while (i < lines.size()) {
            // Find the next property block start (e.g., "0:", "1:", etc.)
            if (!lines.get(i).matches("^\\d+:$")) {
                i++;
                continue;
            }

            // Read the indented block (property details)
            List<String> propertyBlock = new ArrayList<>();
            i++; // skip the index line
            while (i < lines.size() && lines.get(i).startsWith("  ")) {
                propertyBlock.add(lines.get(i).substring(2)); // remove indentation
                i++;
            }

            var property = deserializeAccountHolderProperty(propertyBlock);
            result.add(property);
        }

        return List.copyOf(result);
    }

    /**
     * Deserializes an account holder from lines of text.
     *
     * @param lines The text to deserialize
     * @return The deserialized account holder
     * @throws IOException When IO fails
     */
    public static @Nullable VXAccountHolder deserializeAccountHolder(List<String> lines) throws IOException {
        var remaining = new ArrayList<>(lines);
        var typeSplit = remaining.getFirst().split(": ");
        if (!Objects.equals(typeSplit[0].trim(), "type")) throw new IOException("Cannot infer type of account holder.");

        var typeAsString = typeSplit[1].trim();
        var type = AccountHolderType.valueOf(typeAsString);

        // VaultX does not know how to deserialize third party users.
        if (type == AccountHolderType.THIRD_PARTY_PLUGIN) return null;

        remaining.removeFirst();

        var uuidSplit = remaining.getFirst().split(": ");
        if (!Objects.equals(uuidSplit[0].trim(), "uniqueId"))
            throw new IOException("Cannot read UUID of account holder.");
        var uuid = UUID.fromString(uuidSplit[1].trim());

        // If for whatever reason an inert reference was saved, it is read back as a ref.
        if (type == AccountHolderType.INERT_REF) {
            return new VXAccountHolderReference(uuid);
        }

        remaining.removeFirst();

        // Delegate deserialization to local package
        return VXEconomyLoader.continueDeserializationOfAccountHolder(type, uuid, remaining);
    }

    /**
     * Revives inert references within the given set of accounts.
     *
     * @param accounts The set of accounts
     */
    public static void reviveRefs(Set<VXAccountHolder> accounts) {
        Set<VXAccountHolder> copy = accounts.stream()
                .filter(a -> a.getType() != AccountHolderType.INERT_REF)
                .collect(Collectors.toUnmodifiableSet());

        accounts.clear();

        copy.forEach(account -> {
            account.getProperties().forEach(property -> {
                if (property.getType() == AccountHolderPropertyType.HOLDER && property instanceof HolderProperty hp) {
                    var holder = hp.getValue();

                    if (!(holder instanceof VXAccountHolderReference ref)) return;
                    reviveRef(ref, copy, hp::setValue);
                } else if (property.getType() == AccountHolderPropertyType.HOLDER_ARRAY && property instanceof HolderArrayProperty hap) {
                    var values = hap.getValue();
                    if (values == null) return;

                    for (int i = 0; i < values.length; i++) {
                        if (!(values[i] instanceof VXAccountHolderReference ref)) continue;

                        var index = i;
                        reviveRef(ref, copy, h -> values[index] = h);
                    }
                }
            });

            accounts.add(account);
        });
    }

    private static void reviveRef(VXAccountHolderReference ref, Set<VXAccountHolder> accounts, Consumer<VXAccountHolder> setter) {
        var holderId = ref.getUniqueId();
        var actualHolder = accounts.stream()
                .filter(a -> Objects.equals(a.getUniqueId(), holderId))
                .findAny()
                .orElse(null);

        if (actualHolder == null) return;

        setter.accept(actualHolder);
    }

    /**
     * Indents the given text.
     *
     * @param lines The lines to indent
     * @return The indented text
     */
    public static @NotNull List<String> indentText(List<String> lines) {
        return lines.stream().map(l -> "  " + l).toList();
    }

    /**
     * Removes one indent from the given text.
     *
     * @param lines The lines to de-indent
     * @return The de-indented text
     */
    public static @NotNull List<String> removeIndent(List<String> lines) {
        return lines.stream().map(l -> {
            if (!l.startsWith("  ")) return l;
            return l.substring(2);
        }).toList();
    }

    /**
     * Tries to get value using getter, but returns fallback when exception occurs.
     *
     * @param getter   The getter
     * @param fallback The fallback value
     * @param <T>      The type of value
     * @return The value
     */
    public static <T> T tryCatchOrElse(Supplier<T> getter, T fallback) {
        try {
            return getter.get();
        } catch (Throwable e) {
            return fallback;
        }
    }

    /**
     * Private constructor to prevent instantiation.
     *
     * @throws Exception Always
     */
    private VXIO() throws Exception {
        throw new Exception("Cannot instantiate utility class VXIO.");
    }
}
