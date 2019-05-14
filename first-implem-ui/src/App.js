import React, { Component } from 'react';
import logo from './logo.svg';
import './App.css';
import Blockchain from './blockchain/Blockchain';

class App extends Component {
  render() {
    return (
        <div id="mainContainer">
            <nav className="navbar navbar-light bg-light">
                <span className="navbar-brand mb-0 h1">Navbar</span>
            </nav>

            <Blockchain/>
        </div>


    );
  }
}

export default App;
