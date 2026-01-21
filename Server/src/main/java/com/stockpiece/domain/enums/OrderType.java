package com.stockpiece.domain.enums;

public enum OrderType {
    MARKET_BUY,
    MARKET_SELL,
    LIMIT_BUY,
    LIMIT_SELL;

    public boolean isBuy() {
        return this == MARKET_BUY || this == LIMIT_BUY;
    }

    public boolean isSell() {
        return this == MARKET_SELL || this == LIMIT_SELL;
    }

    public boolean isMarket() {
        return this == MARKET_BUY || this == MARKET_SELL;
    }

    public boolean isLimit() {
        return this == LIMIT_BUY || this == LIMIT_SELL;
    }
}
