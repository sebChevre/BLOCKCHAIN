import React, { Component } from 'react';

class Transaction extends Component {

    render() {

        const transaction = this.props.transaction;

       return (
           <li key={transaction.hash} className="list-group-item">
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