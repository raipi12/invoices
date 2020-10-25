package invoices.task.model.source;

public enum Currency {
    EURO(19.40),DOLLARS(17.65),MD(0.0);

    private Double exchangeRate;

    Currency (Double exchangeRate){
        this.exchangeRate = exchangeRate;
    }

    public Double getExchangeRate() {
        return exchangeRate;
    }

    public void setExchangeRate(Double exchangeRate) {
        this.exchangeRate = exchangeRate;
    }
}