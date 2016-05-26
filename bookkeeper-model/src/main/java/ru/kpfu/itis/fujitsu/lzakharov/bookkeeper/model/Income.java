package ru.kpfu.itis.fujitsu.lzakharov.bookkeeper.model;

import java.sql.Date;

public class Income extends AbstractModel {
    private Long clientId;
    private Client client;
    private Long categoryId;
    private Category category;
    private int price;
    private String description;
    private Date creationDate;

    public Income() {
    }

    public Income(Long id, Long clientId, Client client, Long categoryId, Category category, int price,
                  String description, Date creationDate) {

        this.id = id;
        this.clientId = clientId;
        this.client = client;
        this.categoryId = categoryId;
        this.category = category;
        this.price = price;
        this.description = description;
        this.creationDate = creationDate;
    }

    public Income(Long id, Long clientId, Long categoryId, int price, String description, Date creationDate) {
        this.id = id;
        this.clientId = clientId;
        this.categoryId = categoryId;
        this.price = price;
        this.description = description;
        this.creationDate = creationDate;
    }

    public Income(Long clientId, Long categoryId, int price, String description, Date creationDate) {
        this.clientId = clientId;
        this.categoryId = categoryId;
        this.price = price;
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

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
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

        Income income = (Income) o;

        if (price != income.price) return false;
        if (!clientId.equals(income.clientId)) return false;
        if (client != null ? !client.equals(income.client) : income.client != null) return false;
        if (!categoryId.equals(income.categoryId)) return false;
        if (category != null ? !category.equals(income.category) : income.category != null) return false;
        if (!description.equals(income.description)) return false;
        return creationDate.equals(income.creationDate);

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + clientId.hashCode();
        result = 31 * result + client.hashCode();
        result = 31 * result + categoryId.hashCode();
        result = 31 * result + category.hashCode();
        result = 31 * result + price;
        result = 31 * result + description.hashCode();
        result = 31 * result + creationDate.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Income{" +
                "id=" + id +
                ", clientId=" + clientId +
                ", client=" + client +
                ", categoryId=" + categoryId +
                ", category=" + category +
                ", price=" + price +
                ", description='" + description + '\'' +
                ", creationDate=" + creationDate +
                '}';
    }
}
