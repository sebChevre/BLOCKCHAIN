import React, { Component } from 'react';
import axios from 'axios';
import BlockUi from 'react-block-ui';
import 'react-block-ui/style.css';

class PortefeuilleCmp extends Component {
    constructor (props){
        super(props);

        this.state = {
            show: false,
            sendValues:{
                to: "",
                from: this.props.portefeuille.adresse,
                montant: ""
            },
            portefeuille:this.props.portefeuille,
            blocking: false,
            formValid: false

        }

        this.click = this.click.bind(this);
        this.envoyerMontant = this.envoyerMontant.bind(this);
        this.changeDestinataire = this.changeDestinataire.bind(this);
        this.changeMontant = this.changeMontant.bind(this);
        this.refreshCmp = this.refreshCmp.bind(this);

    }

    toggleBlocking() {
        this.setState({blocking: !this.state.blocking});
    }

    changeMontant(e){
        console.log("Montant change: " + e.target.value)
        this.setState(Object.assign(this.state.sendValues,
            {montant:e.target.value}
        ))
        console.log(this.state)

        if(!this.isEmpty(this.state.sendValues.to)){
            this.setState({
                formValid:true
            })
        }
    }

    isEmpty (str) {
        return str.length === 0;
    }

    changeDestinataire(e){
        console.log("Destinataire change: " + e.target.value)
        this.setState(Object.assign(this.state.sendValues,
            {to:e.target.value}
        ))
        console.log(this.state)

        if(!this.isEmpty(this.state.sendValues.montant)){
            this.setState({
                formValid:true
            })
        }
    }

    componentWillReceiveProps(props) {
        console.log(props)
        this.setState({...this.state,portefeuille:props.portefeuille});
        this.setState(Object.assign(this.state.sendValues,
            {
                montant:"",
                to: ""
            }
        ))
    }

    refreshCmp () {
        console.log("Refresh")
        this.props.refreshListParent();
        //this.setState({state:this.state})
        console.log(this.state)
    }
    envoyerMontant () {

        this.toggleBlocking();
        let that = this;

        axios.post('/transaction/' + this.state.sendValues.from + "/" + this.state.sendValues.to + "/" + this.state.sendValues.montant, {

        })
        .then(function (response) {
            console.log(response);
            that.toggleBlocking();
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


       // const portefeuille = this.props.portefeuille;
        const portefeuilles = this.props.portefeuilles;

        console.log(this.state.portefeuille);
        const isBase = this.state.portefeuille.basePortefeuille;
        let baseBadge;

        if(isBase){
            baseBadge = <span className="badge badge-danger badge-pill">BASE</span>;
        }else{
            baseBadge = <span className="badge badge-primary badge-pill">PRINCIPAL</span>;
        }

        return (
            <div className="card block">
                <div className="card-header" onClick={this.click}>
                    {baseBadge}
                    <span className="blocknumber">Portefeuille : {this.state.portefeuille.adresse}</span>
                    <span className="portefeuile-desc">{this.state.portefeuille.description}</span>

                </div>
                <div className={this.state.show ? "card-body" : "hidden"}>
                    <h1>
                        <span className="badge badge-primary badge-pill">{this.state.portefeuille.balance +" CS"}</span>
                    </h1>
                    <span className="block_hash_lbl">Clé: </span><span className="block_hash_value"> {this.state.portefeuille.clePublique}</span><br />
                    <span className="block_hash_lbl">adresse: </span><span className="block_hash_value"> {this.state.portefeuille.adresse}</span>

                    <hr/>

                    <BlockUi tag="div" blocking={this.state.blocking}>
                        <form className="form-inline">
                             <div className="form-group mb-2">
                                 <label htmlFor="montant">Envoyer</label>
                                 <input type="number" min="0" max="100" id="montant" onChange={this.changeMontant} value={this.state.sendValues.montant} />
                                 <label htmlFor="montant">CS à</label>
                            </div>
                            <div id="portefeuilles_list" className="form-group mb-2">
                                <select id="inputState" className="form-control" onChange={this.changeDestinataire} value={this.state.sendValues.to}>
                                    <option>Portefeuille...</option>
                                    {portefeuilles.map(pfeuille => {
                                        return <option key={pfeuille.adresse}>{pfeuille.adresse}</option>
                                    })}
                                </select>
                            </div>
                            <button type="button" className="btn btn-primary mb-2" onClick={this.envoyerMontant} disabled={!this.state.formValid}>Valider</button>
                        </form>
                    </BlockUi>

                </div>
            </div>
        )
    }
}

export default PortefeuilleCmp;