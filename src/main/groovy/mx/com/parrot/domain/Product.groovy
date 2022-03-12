package mx.com.parrot.domain

import javax.persistence.*
import java.io.Serializable

@Entity
@Table(name = "products", schema = "parrot")
class Product implements Serializable{

  static final long serialVersionUID = 1L

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  Long id

  @Column(name = "product")
  String product

  @Column(name = "price")
  Float price

  @Column(name = "stock")
  Integer stock

  @Override
  String toString(){
    """
    Product{
      id=${id}
      product=${product}
      price=${price}
      stock=${stock}
    }
    """
  }
}
