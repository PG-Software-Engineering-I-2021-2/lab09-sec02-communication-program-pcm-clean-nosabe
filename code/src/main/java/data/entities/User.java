package data.entities;

import org.hibernate.validator.constraints.UniqueElements;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import static config.GlobalConstants.DB_CHAR_LENGTH;
import static config.GlobalConstants.DB_DNI_LENGTH;

@Entity
@Table(name = "users")
public class User {

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
