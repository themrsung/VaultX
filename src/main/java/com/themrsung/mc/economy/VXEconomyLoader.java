package com.themrsung.mc.economy;

import com.themrsung.mc.io.VXIO;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class VXEconomyLoader {
    /**
     * Continues deserialization from VXIO.
     *
     * @param type      The type
     * @param uniqueId  The unique identifier
     * @param remaining The remaining strings
     * @return The deserialized account holder
     * @throws IOException when IO fails
     */
    public static @NotNull VXAccountHolder continueDeserializationOfAccountHolder(AccountHolderType type, UUID uniqueId, List<String> remaining) throws IOException {
        var nameSplit = remaining.getFirst().split(": ");
        if (!Objects.equals(nameSplit[0].trim(), "name")) throw new IOException("Cannot read name of account holder.");
        var name = VXIO.tryCatchOrElse(() -> nameSplit[1], "");

        remaining.removeFirst();

        var balanceSplit = remaining.getFirst().split(": ");
        if (!Objects.equals(balanceSplit[0].trim(), "balance"))
            throw new IOException("Cannot read balance of account holder.");
        var balance = Double.parseDouble(balanceSplit[1].trim());

        remaining.removeFirst();

        var premiumBalanceSplit = remaining.getFirst().split(": ");
        if (!Objects.equals(premiumBalanceSplit[0].trim(), "premiumBalance"))
            throw new IOException("Cannot read premium balance of account holder.");
        var premiumBalance = Long.parseLong(premiumBalanceSplit[1].trim());

        remaining.removeFirst();

        if (type == AccountHolderType.PLAYER) {
            if (!remaining.getFirst().trim().equals("properties:"))
                throw new IOException("Cannot read properties of player.");
            remaining.removeFirst();

            var properties = VXIO.deserializeAccountHolderProperties(VXIO.removeIndent(remaining));

            var player = new VXPlayerImpl(Bukkit.getOfflinePlayer(uniqueId), balance, premiumBalance);
            player.properties.addAll(properties);
            return player;
        }

        assert type == AccountHolderType.INSTITUTION;

        var ownerSplit = remaining.getFirst().split(": ");
        if (!Objects.equals(ownerSplit[0].trim(), "owner")) throw new IOException("Cannot read owner of institution.");
        var owner = Bukkit.getOfflinePlayer(UUID.fromString(ownerSplit[1].trim()));

        remaining.removeFirst();

        var membersSplit = remaining.getFirst().split(": ");
        if (!Objects.equals(membersSplit[0].trim(), "members"))
            throw new IOException("Cannot read members of institution.");
        var members = new ArrayList<OfflinePlayer>();

        remaining.removeFirst();

        while (!remaining.getFirst().trim().equals("properties:") && !remaining.isEmpty()) {
            var member = Bukkit.getOfflinePlayer(UUID.fromString(remaining.getFirst().trim()));
            members.add(member);

            remaining.removeFirst();
        }

        if (!remaining.getFirst().trim().equals("properties:"))
            throw new IOException("Cannot read properties of institution.");
        remaining.removeFirst();

        var properties = VXIO.deserializeAccountHolderProperties(VXIO.removeIndent(remaining));

        var institution = new VXInstitutionImpl(uniqueId, name, owner);

        institution.balance = balance;
        institution.premiumBalance = premiumBalance;
        institution.members.addAll(members);
        institution.properties.addAll(properties);

        return institution;
    }
}
