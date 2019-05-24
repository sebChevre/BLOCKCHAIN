import React, { Component } from 'react';
import axios from "axios";
import PortefeuilleCmp from "./PortefeuilleCmp";
import ReactJson from "react-json-view";

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

        axios.get(`/noeuds/connected`)
            .then(response => {
                const connectedNoeud = response.data;
                this.setState({ moeudsConnectes: connectedNoeud });
                console.log(this.state);

            })
    }

    loadDetailNoeud () {

        console.log("load detail noeud....")

        axios.get(`/noeuds`)
            .then(response => {
                const noeud = response.data;
                this.setState({ noeud: noeud });
                console.log(this.state);

            })
    }

    render () {

        return (
            <div>
                <h1>Noeud</h1>
                <div className="jumbotron">
                    <h3 className="display-4">{this.state.noeud.noeudId}</h3>
                    <p className="lead">Adresse: {this.state.noeud.host}:{this.state.noeud.port} </p>
                    <hr className="my-4" />

                    <ul>
                        { this.state.moeudsConnectes.map(noeud => {
                            return (<li>{noeud.noeudId} - {noeud.host}:{noeud.port}</li>)
                        })}
                    </ul>
                </div>

            </div>
        )
    }
}

export default NoeudsCmp;