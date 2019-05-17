import React, { Component } from 'react';
import logo from './logo.svg';
import './App.css';
import Blockchain from './blockchain/BlockchainViewCmp';
import PortefeuillesCmp from "./blockchain/PortefeuillesViewCmp";
import { BrowserRouter as Router, Route, Link } from "react-router-dom";

class App extends Component {
  render() {
    return (
        <div id="container-fluid mainContainer">
            <Router>
            <nav className="navbar navbar-expand-lg navbar-light bg-light">
                <a className="navbar-brand" href="#">CryptoSous</a>
                <button className="navbar-toggler" type="button" data-toggle="collapse"
                        data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent"
                        aria-expanded="false" aria-label="Toggle navigation">
                    <span className="navbar-toggler-icon"></span>
                </button>

                <div className="collapse navbar-collapse" id="navbarSupportedContent">
                    <ul className="navbar-nav mr-auto">
                        <li className="nav-item active">
                            <Link className="nav-link"  to="/blockchain">Blockchain</Link>

                        </li>
                        <li className="nav-item">
                            <Link className="nav-link"  to="/portefeuilles">Portefeuilles</Link>

                        </li>

                    </ul>

                </div>
            </nav>

            <div id="mainContainer">

                    <Route exact path="/" component={Blockchain} />
                    <Route path="/blockchain" component={Blockchain}/>
                    <Route path="/portefeuilles" component={PortefeuillesCmp} />

            </div>

        </Router>
        </div>


    );
  }
}

export default App;
