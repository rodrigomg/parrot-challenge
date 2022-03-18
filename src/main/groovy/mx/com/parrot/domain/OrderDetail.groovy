package mx.com.parrot.domain

import javax.persistence.*
import java.io.Serializable

@Entity
@Table(name = "order_details", schema = "parrot")
class OrderDetail implements Serializable{

  static final long serialVersionUID = 1L

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  Long id

  @ManyToOne
  @JoinColumn(name = "order_id")
  Order order

  @ManyToOne
  @JoinColumn(name = "product_id")
  Product product

  @Override
  String toString(){
    """
    OrderDetail{
      id=${id}
      order=${order.toString()}
      product=${product.toString()}
    }
    """
  }

}
