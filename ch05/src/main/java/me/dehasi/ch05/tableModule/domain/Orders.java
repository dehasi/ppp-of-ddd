package me.dehasi.ch05.tableModule.domain;

class Orders {

    private final DataSet dataSet;

    Orders(DataSet dataSet) {
        this.dataSet = dataSet;
    }

    static class DataSet {
        // C# Specific
        // Namespace: System.Data
        // Assembly: System.Data.Common.dll
        // Represents an in-memory cache of data.
    }
}
