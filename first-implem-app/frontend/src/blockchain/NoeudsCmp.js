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
                        <p>It uses utility classes for typography and spacing to space content out within the larger
                            container.</p>
                        <a className="btn btn-primary btn-lg" href="#" role="button">Learn more</a>
                </div>

            </div>
        )
    }
}

export default NoeudsCmp;