import React, { Component } from 'react';
import Transaction from "./Transaction";

class Block extends Component {



    constructor (props){
        super(props);

        this.state = {
            show: false
        }

        this.click = this.click.bind(this)

    }


    click(e){
        const currentState = this.state.show;
        this.setState({show : !currentState})
    }

    render() {

        const block = this.props.block;
        const transactions = this.props.block.transactions;


        return (
        <div className="card block">
            <div className="card-header" onClick={this.click}>
                <span className="blocknumber">Bloc n° : {block.blockNumber}</span>
            </div>
            <div className={this.state.show ? "card-body" : "hidden"}>
                <span className="block_hash_lbl">hash:</span><span className="block_hash_value"> {block.hash}</span><br />
                <span className="block_hash_lbl">previous hash:</span><span className="block_hash_value"> {block.hashPrecedent}</span>
                <br/>
                <h5>Transactions</h5>

                <ul className="list-group">
                {transactions.map(transaction => {
                    return <Transaction  key={transaction.hash} transaction={transaction} />
                })}

                </ul>
            </div>
        </div>
        )
    }
}

export default Block;