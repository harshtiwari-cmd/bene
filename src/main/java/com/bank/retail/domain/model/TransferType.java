package com.bank.retail.domain.model;

import java.util.Arrays;
import java.util.Locale;
import java.util.Optional;

public enum TransferType {
	OWN_ACCOUNT("Within Own Account", "Within Own Account", new String[]{"OWN", "OWN_ACCOUNT", "WITHIN OWN ACCOUNT"}),
	WITHIN_DUKHAN("Within Dukhan", "Within Dukhan", new String[]{"WITHINBANK", "WITHIN DUKHAN", "DUKHAN"}),
	WITHIN_QATAR("Within Qatar", "Within Qatar", new String[]{"WITHINQATAR", "WITHIN QATAR", "QATAR"}),
	CARDLESS_WITHDRAWAL("Cardless Withdrawal", "Cardless Withdrawal", new String[]{"CARDLESS", "CARDLESS WITHDRAWAL"}),
	INTERNATIONAL_TRANSFER("International Transfer", "International Transfer", new String[]{"INTL", "INTERNATIONAL", "INTERNATIONAL TRANSFER"}),
	WESTERN_UNION("Western Union", "Western Union", new String[]{"WU", "WESTERN UNION"}),
	STANDING_ORDER("Standing Order", "Standing Order", new String[]{"STANDING", "STANDING ORDER", "STANDING_ORDER"});

	private final String code;
	private final String displayName;
	private final String[] aliases;

	TransferType(String code, String displayName, String[] aliases) {
		this.code = code;
		this.displayName = displayName;
		this.aliases = aliases;
	}

	public String getCode() {
		return code;
	}

	public String getDisplayName() {
		return displayName;
	}

	public static Optional<TransferType> fromString(String input) {
		if (input == null) {
			return Optional.empty();
		}
		String normalized = input.trim().toUpperCase(Locale.ROOT);
		return Arrays.stream(values())
				.filter(t -> t.name().equals(normalized)
						|| t.code.equalsIgnoreCase(normalized)
						|| t.displayName.toUpperCase(Locale.ROOT).equals(normalized)
						|| Arrays.stream(t.aliases).anyMatch(a -> a.equalsIgnoreCase(normalized)))
				.findFirst();
	}
}


