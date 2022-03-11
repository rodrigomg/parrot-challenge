package mx.com.parrot.domain

import javax.persistence.*
import java.io.Serializable
import java.util.Set
import groovy.transform.ToString

@Entity
@Table(name = "users", schema = "parrot")
class User implements Serializable {

  static final long serialVersionUID = 1L

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  Long id

  @Column(name = "email")
  String email

  @Column(name = "first_name")
  String firstName

  @Column(name = "last_name")
  String lastName

  @Override
  String toString() {
    """
    Users{
      id=${id}
      email=${email}
      firstName=${firstName}
      lastName=${lastName}
    }
    """
  }
}
