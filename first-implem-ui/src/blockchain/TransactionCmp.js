import React, { Component } from 'react';

class TransactionCmp extends Component {


    render() {

        const transaction = this.props.transaction;


       return (
           <li className="list-group-item">
               <h1>
                   <span className="badge badge-primary badge-pill">{transaction.value +" CS"}</span>
               </h1>
               <span className="from-lbl">From:</span><span className="from-value">{transaction.expediteur}</span><br/>
                <span className="to-lbl">To:</span><span className="to-value">{transaction.destinataire}</span>

            </li>
       )
    }
}

export default TransactionCmp;