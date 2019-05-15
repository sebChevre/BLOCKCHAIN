import React, { Component } from 'react';

class Transaction extends Component {

    constructor (props){
        super(props);


    }
    render() {

        const transaction = this.props.transaction;
        const key = this.props.key;

       return (
           <li key={key} className="list-group-item">
               <h1>
                   <span className="badge badge-primary badge-pill">{transaction.value}</span>
               </h1>
               <span className="from-lbl">From:</span><span className="from-value">{transaction.expediteur}</span><br/>
                <span className="to-lbl">To:</span><span className="to-value">{transaction.destinataire}</span>

            </li>
       )
    }
}

export default Transaction;