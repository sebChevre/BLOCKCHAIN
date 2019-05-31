import React, { Component } from 'react';
import axios from "axios";
import PortefeuilleCmp from "./PortefeuilleCmp";
import ReactJson from "react-json-view";
let QRCode = require('qrcode.react');

class NoeudsCmp extends Component {



    state = {
        noeud: {},
        moeudsConnectes : []
    }


    componentDidMount() {
        this.loadDetailNoeud();
        this.loadConnectedNoeuds();
    }

    loadConnectedNoeuds () {
        console.log("load list connectec noeud....")

        axios.get(`/noeud/connected`)
            .then(response => {
                const connectedNoeud = response.data;
                this.setState({ moeudsConnectes: connectedNoeud });
                console.log(this.state);

            })
    }

    loadDetailNoeud () {

        console.log("load detail noeud....")

        axios.get(`/noeud`)
            .then(response => {
                const noeud = response.data;
                this.setState({ noeud: noeud });
                console.log(this.state.noeud);

            })
    }

    render () {


        console.log(JSON.stringify(this.state.noeud.portefeuille));
        let p = JSON.stringify(this.state.noeud.portefeuille);
       // var p = JSON.parse(JSON.stringify(this.state.noeud.portefeuille))


        let kp = ""+this.state.noeud.portefeuille;

        return (
            <div>

                <h1>Noeud</h1>
                <div className="jumbotron">
                    <h3 className="display-4">{this.state.noeud.noeudId}</h3>
                    <p className="lead">Adresse: {this.state.noeud.hote}:{this.state.noeud.port} </p>
                    <QRCode value={kp}/>
                    <hr className="my-4" />

                    <h4>Noeuds connectés</h4>
                    <ul>
                        { this.state.moeudsConnectes.map(noeud => {
                            return (
                                <li key={noeud.noeudId}>
                                    <span className="portefeuille-list-adress">{noeud.noeudId} </span><br/>
                                    <span className="portefeuille-list-adress">{noeud.hote}:{noeud.port}</span><br/>
                                    <span className="portefeuille-list-adress">Adresse portefeuille:</span><span className="">{noeud.portefeuille.adresse}</span><br/>
                                    <span className="portefeuille-list-adress">Clé publique:</span><span className="">{noeud.portefeuille.clePublique}</span>
                                </li>
                            )
                        })}
                    </ul>
                </div>

            </div>
        )
    }
}

export default NoeudsCmp;