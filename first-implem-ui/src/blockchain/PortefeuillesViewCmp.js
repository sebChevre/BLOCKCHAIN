import React, { Component } from 'react';
import axios from 'axios';
import PortefeuilleCmp from './PortefeuilleCmp';
import ReactJson from 'react-json-view'

class PortefeuillesViewCmp extends Component {

    constructor (props){
        super(props);

        this.loadPortefeuilles = this.loadPortefeuilles.bind(this);
    }

    state = {
        portefeuilles: []
    }

    componentDidMount() {
       this.loadPortefeuilles();
    }

    loadPortefeuilles () {

        console.log("load portefeuilles....")

        axios.get(`/portefeuilles`)
            .then(response => {
                const portefeuilles = response.data;
                this.setState({ portefeuilles: portefeuilles });
                console.log(this.state);

            })


    }

    render () {

        console.log("render")
        console.log(this.state.portefeuilles)

        return (
            <div>
            <h1>Portefeuilles</h1>
                <div className="container-fluid">
                    <div className="row">
                        <div className="col-8">
                            { this.state.portefeuilles.map(portefeuille => {

                                console.log(portefeuille)

                                return (
                                    <PortefeuilleCmp key={portefeuille.adresse}
                                                     portefeuille={portefeuille}
                                                     portefeuilles={this.state.portefeuilles}
                                                     refreshListParent={this.loadPortefeuilles}                                           />

                                )
                            })}
                        </div>
                        <div className="col-4">
                            <div className="card">
                                <div className="card-body">
                                    <h6 className="card-subtitle mb-2 text-muted">Structure Json</h6>
                                    <ReactJson src={this.state.portefeuilles} collapsed={true} />
                                </div>
                            </div>

                        </div>
                    </div>
                </div>

            </div>
        )
    }
}

export default PortefeuillesViewCmp;