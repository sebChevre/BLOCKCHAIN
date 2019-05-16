import React, { Component } from 'react';
import axios from 'axios';
import PortefeuilleCmp from './PortefeuilleCmp';
import ReactJson from 'react-json-view'

class PortefeuillesViewCmp extends Component {

    state = {
        portefeuilles: []
    }

    componentDidMount() {
        axios.get(`/portefeuilles`)
        .then(response => {
            const portefeuilles = response.data;
            this.setState({ portefeuilles: portefeuilles });
        })
    }

    render () {
        return (
            <div>
            <h1>Portefeuilles</h1>
                <div className="container-fluid">
                    <div className="row">
                        <div className="col-8">
                            { this.state.portefeuilles.map(portefeuille => {
                                return (
                                    <PortefeuilleCmp key={portefeuille.adresse} portefeuille={portefeuille} portefeuilles={this.state.portefeuilles} />

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