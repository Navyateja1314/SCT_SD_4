import './App.css';
import { BrowserRouter as Router, Routes, Route} from 'react-router-dom'
import Navbar from './layout/Navbar';
import Home from './Components/Home';
import Products from './Components/Products';

function App() {
  return (
    <div className="App">
    <Router>
      <Routes>
      <Route path="/" element={<Home />}/>    
      <Route path="/products" element={<Products />}/>
      </Routes>
    </Router>
    </div> 
  );
}

export default App;
