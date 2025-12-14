import axios from "axios";
export default function ({showDownload, query, shop}) {

  const downloadExcel = async () => {
    if (!query || !shop) {
      alert("No product data available");
      return;
    }

    try {
      const response = await axios.get(
        "http://localhost:8383/api/download",
        {
          params: { query, shop },
          responseType: "blob" 
        }
      );

      const url = window.URL.createObjectURL(
        new Blob([response.data])
      );

      const link = document.createElement("a");
      link.href = url;
      link.setAttribute("download", "Products.xlsx");
      document.body.appendChild(link);
      link.click();
      link.remove();

    } catch (error) {
      console.error("Excel download failed", error);
      alert("Failed to download Excel");
    }
  };

  return (
    <div>
      <nav className="navbar">
       
        <h2 className="title">ShopScrape</h2>
        {showDownload && (
           <div className="container">
           <button className="download-btn" onClick={() => downloadExcel()}>
             ImportFile
           </button> 
           </div>)}
    </nav>  
  </div>  
    )
}
 