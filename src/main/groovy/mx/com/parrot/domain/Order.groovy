package mx.com.parrot.domain

import javax.persistence.*
import java.io.Serializable

@Entity
@Table(name = "orders", schema = "parrot")
class Order implements Serializable{

  static final long serialVersionUID = 1L

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  Long id

  @ManyToOne
  @JoinColumn(name = "user_id")
  User user

  @Override
  String toString(){
    """
    Order{
      id=${id}
      user=${user.toString()}
    }
    """
  }

}
