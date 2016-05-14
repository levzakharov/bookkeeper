package ru.kpfu.itis.fujitsu.lzakharov.bookkeeper.model;

import ru.kpfu.itis.fujitsu.lzakharov.bookkeeper.model.enums.Gender;

/**
 * Represents the Client.
 */
public class Client extends AbstractModel {
    private Long id;
    private String login;
    private String password;
    private Gender gender;

    public Client() {
    }

    public Client(Long id, String login, String password, Gender gender) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.gender = gender;
    }

    public Client(String login, String password, Gender gender) {
        this.login = login;
        this.password = password;
        this.gender = gender;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Client client = (Client) o;

        if (!id.equals(client.id)) return false;
        if (!login.equals(client.login)) return false;
        if (!password.equals(client.password)) return false;
        return gender == client.gender;

    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + login.hashCode();
        result = 31 * result + password.hashCode();
        result = 31 * result + gender.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", gender=" + gender +
                '}';
    }
}
