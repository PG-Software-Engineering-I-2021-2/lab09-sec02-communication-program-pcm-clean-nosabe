package data.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import java.io.Serializable;

import static config.GlobalConstants.DB_CHAR_LENGTH;
import static config.GlobalConstants.DB_DNI_LENGTH;

@Entity
@Table(name = "users")
public class User implements Serializable {

    @Id
    @NotEmpty
    @Column(name = "usernameDni", nullable = false, length = DB_DNI_LENGTH, unique = true)
    private String username;

    @NotEmpty
    @Column(name = "password", nullable = false, length = DB_CHAR_LENGTH)
    private String password;

    @NotEmpty
    @Column(name = "nombres", length = DB_CHAR_LENGTH)
    private String nombres;

    @NotEmpty
    @Column(name = "apellidos", length = DB_CHAR_LENGTH)
    private String apellidos;

    @NotEmpty
    @Column(name = "email", nullable = false, length = DB_CHAR_LENGTH)
    private String email;
    
    public User(){
        //default implementation ignored
    }

    public void setUsername(String dniUser) {
        this.username = dniUser;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getNombres() {
        return nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public String getEmail() {
        return email;
    }

}
