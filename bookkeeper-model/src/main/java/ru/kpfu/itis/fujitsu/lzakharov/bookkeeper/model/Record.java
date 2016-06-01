package ru.kpfu.itis.fujitsu.lzakharov.bookkeeper.model;

import java.sql.Date;
import ru.kpfu.itis.fujitsu.lzakharov.bookkeeper.model.enums.Type;

/**
 * Represents the Record.
 */
public class Record extends AbstractModel {
    private Long clientId;
    private Client client;
    private Long categoryId;
    private Category category;
    private Type type;
    private int amount;
    private String description;
    private Date creationDate;

    public Record() {
    }

    public Record(Long id, Long clientId, Long categoryId, Type type, int amount, String description, Date creationDate) {
        this.id = id;
        this.clientId = clientId;
        this.categoryId = categoryId;
        this.type = type;
        this.amount = amount;
        this.description = description;
        this.creationDate = creationDate;
    }

    public Record(Long clientId, Long categoryId, Type type, int amount, String description, Date creationDate) {
        this.clientId = clientId;
        this.categoryId = categoryId;
        this.type = type;
        this.amount = amount;
        this.description = description;
        this.creationDate = creationDate;
    }

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Record record = (Record) o;

        if (amount != record.amount) return false;
        if (!clientId.equals(record.clientId)) return false;
        if (!categoryId.equals(record.categoryId)) return false;
        if (type != record.type) return false;
        if (!description.equals(record.description)) return false;
        return creationDate.equals(record.creationDate);

    }

    @Override
    public int hashCode() {
        int result = clientId.hashCode();
        result = 31 * result + categoryId.hashCode();
        result = 31 * result + type.hashCode();
        result = 31 * result + amount;
        result = 31 * result + description.hashCode();
        result = 31 * result + creationDate.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Record{" +
                "clientId=" + clientId +
                ", client=" + client +
                ", categoryId=" + categoryId +
                ", category=" + category +
                ", type=" + type +
                ", amount=" + amount +
                ", description='" + description + '\'' +
                ", creationDate=" + creationDate +
                '}';
    }
}
