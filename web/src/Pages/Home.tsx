import { Link } from "react-router-dom"

 const Home = () => {
    return (
        <div>
            HomePage
            <Link to="/login">Login</Link>
        </div>
    )
}

export default Home