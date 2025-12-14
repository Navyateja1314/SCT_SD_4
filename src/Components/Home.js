import axios from "axios";
import { useState } from "react";
import { useNavigate } from "react-router-dom";
import amazon from "../assets/amazon.png";
import ebay from "../assets/ebay.png";
import walmart from "../assets/walmart.png";
import Navbar from '../layout/Navbar';


function Home() {
  
  const [productData, setProductData] = useState({
    title: "",
    price: "",
    rating:"",
    link: ""
  });
  const [loading, setLoading] = useState(false);
  const [query, setQuery] = useState("");
  const navigate = useNavigate();

    const onInputChange= (e)=>{
      setQuery(e.target.value)
    };

  const fetchProducts = async (shop) => {
    if (!query.trim()) {
      alert("Please Enter Product name");
      return;
    }
      
    try {
       let result;
       setLoading(true);
       if(shop === "amazon"){
        const result = await axios.get(`http://localhost:8383/api/amazon`,{
          params : {query}
        });
        setProductData(result.data)
        navigate("/Products",{ state: { products: result.data, query: query, shop: shop  } });
       }
       else if(shop === "ebay"){
         const result= await axios.get(`http://localhost:8383/api/ebay`,{
          params : {query}
         });
        setProductData(result.data);
        navigate("/Products",{ state: { products: result.data } });
       }
       else if(shop === "walmart"){
        const result= await axios.get(`http://localhost:8383/api/walmart`,{
          params : {query}
        });
        setProductData(result.data);
        navigate("/Products",{ state: { products: result.data } });
       }
    } catch (err) {
      console.error("Error fetching products", err);
    } finally {
      setLoading(false);
    }
  };
  

  return (
     <>
     <Navbar showDownload={false} />
    <div className="card">
      <div className="homeContainer">
        <h2>E-Commerce Product Extractor</h2>

        <label>Product Type</label>
        <input
          type="text"
          placeholder="Enter Product Type"
          value={query}
          onChange={(e)=>onInputChange(e)}
        />

        <p>Select Website to Scrape</p>

        <div className="shop-container">
          <button className="logobutton"onClick={() => fetchProducts("amazon")} disabled={loading}>
            <img src={amazon} alt="Amazon" className="shop-logo" />
          </button>

          <button className="logobutton" onClick={() => fetchProducts("ebay")} disabled={loading}>
            <img src={ebay} alt="eBay" className="shop-logo" />
          </button>

          <button className="logobutton" onClick={() => fetchProducts("walmart")} disabled={loading}>
            <img src={walmart} alt="Walmart" className="shop-logo" />
          </button>
        </div>
      </div>
    </div>
    </>
  );
}

export default Home;
