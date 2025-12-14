import React from "react";
import Navbar from '../layout/Navbar';
import { useLocation, useNavigate } from "react-router-dom";


function Products() {
  const location = useLocation();
  const navigate = useNavigate();
   const { products=[], query, shop } = location.state || {};


  return (
    <>
    <Navbar showDownload={true} query={query} shop={shop} />
    <div className="products-container">
    
      {products.length === 0 ? (
        <p className="errorDisplay">No products found.</p>
      ) : (
        <table className="products-table">
          <thead>
            <tr>
              <th>Product Name</th>
              <th>Price</th>
              <th>Rating</th>
              <th>Link</th>
            </tr>
          </thead>
          <tbody>
            {products.map((product, index) => (
              <tr key={index}>
                <td>{product.title || "N/A"}</td>
                <td>{product.price ? `₹${(parseFloat(product.price.replace(/[^0-9.]/g, "")) * 83).toFixed(2)}` : "N/A"}</td>
                <td>{product.rating !== null && product.rating !== undefined ? product.rating : "N/A"}</td>
                <td>
                  {product.link ? (
                    <a href={product.link} target="_blank" rel="noopener noreferrer">
                      View
                    </a>
                  ) : (
                    "N/A"
                  )}
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      )}

      <button className="back-button" onClick={() => navigate(-1)}>
        Back
      </button>
    </div>
    </>
  );
}

export default Products;
