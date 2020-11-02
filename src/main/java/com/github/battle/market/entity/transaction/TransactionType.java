package com.github.battle.market.entity.transaction;

public enum TransactionType {
    BUY, SELL, UNKNOWN;

    public static String getTransactionName(TransactionType transactionType) {
        if (transactionType == null) transactionType = UNKNOWN;
        return transactionType.name();
    }
}
