package mx.com.parrot.domain

import javax.persistence.*
import java.io.Serializable

@Entity
@Table(name = "users", schema = "parrot")
class User implements Serializable{

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
    User{
      id=${id}
      email=${email}
      firstName=${firstName}
      lastName=${lastName}
    }
    """
  }
}
