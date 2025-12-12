import { Link } from "react-router-dom"
import '../style/Home.css'
import type { Product } from "../types/Product"

 const Home = () => {
    const products: Product[] = [
        {
            name: "burger",
            price: 300,
        },
        {
            name: "pizza",
            price: 500
        },
        {
            name: "burek",
            price: 100
        }
    ]

    const Card = (product: Product) =>{
        return (
            <div>
                {product.name}
                {product.price}
            </div>
        )
    }

    return (
        <div id="home-container">
            <div>Most ordered meals</div>
            {
                 products.map((product)=>(
                    <Card {...product}></Card>
                ))
            }
        </div>
    )
}

export default Home