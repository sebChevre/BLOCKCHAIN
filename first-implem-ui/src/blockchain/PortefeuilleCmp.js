import React, { Component } from 'react';
import axios from 'axios';

class PortefeuilleCmp extends Component {
    constructor (props){
        super(props);

        this.state = {
            show: false,
            to: "",
            from: this.props.portefeuille.adresse,
            montant: ""
        }

        this.click = this.click.bind(this);
        this.envoyerMontant = this.envoyerMontant.bind(this);
        this.changeDestinataire = this.changeDestinataire.bind(this);
        this.changeMontant = this.changeMontant.bind(this);
        this.refreshCmp = this.refreshCmp.bind(this);

    }

    changeMontant(e){
        console.log("Montant change: " + e.target.value)
        this.setState({
            montant:e.target.value
        })
    }

    changeDestinataire(e){
        console.log("Destinataire change: " + e.target.value)
        this.setState({
            to:e.target.value
        })
        console.log(this.state)
    }

    refreshCmp () {
        this.setState({state:this.state})
    }
    envoyerMontant () {

        let that = this;

        axios.post('/transaction/' + this.state.from + "/" + this.state.to + "/" + this.state.montant, {

        })
        .then(function (response) {
            console.log(response);
            that.refreshCmp();
        })
        .catch(function (error) {
            console.log(error);
        });
    }

    click(){
        const currentState = this.state.show;
        this.setState({show : !currentState})
    }

    render() {

        const portefeuille = this.props.portefeuille;
        const portefeuilles = this.props.portefeuilles;


        return (
            <div className="card block">
                <div className="card-header" onClick={this.click}>
                    <span className="blocknumber">Portefeuille : {portefeuille.adresse}</span>
                </div>
                <div className={this.state.show ? "card-body" : "hidden"}>
                    <h1>
                        <span className="badge badge-primary badge-pill">{portefeuille.balance +" CS"}</span>
                    </h1>
                    <span className="block_hash_lbl">Clé: </span><span className="block_hash_value"> {portefeuille.clePublique}</span><br />
                    <span className="block_hash_lbl">adresse: </span><span className="block_hash_value"> {portefeuille.adresse}</span>

                    <hr/>

                    <form className="form-inline">
                         <div className="form-group mb-2">
                             <label htmlFor="montant">Envoyer</label>
                             <input type="number" min="0" max="100" id="montant" onChange={this.changeMontant}  />
                             <label htmlFor="montant">CS à</label>
                        </div>
                        <div id="portefeuilles_list" className="form-group mb-2">
                            <select id="inputState" className="form-control" onChange={this.changeDestinataire}>
                                <option>Portefeuille...</option>
                                {portefeuilles.map(pfeuille => {
                                    return <option key={pfeuille.adresse}>{pfeuille.adresse}</option>
                                })}
                            </select>
                        </div>
                        <button type="button" className="btn btn-primary mb-2" onClick={this.envoyerMontant}>Valider</button>
                    </form>

                </div>
            </div>
        )
    }
}

export default PortefeuilleCmp;