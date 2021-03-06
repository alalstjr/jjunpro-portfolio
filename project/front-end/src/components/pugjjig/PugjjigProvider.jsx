import React, {Component, Fragment} from 'react'

import InsertModal from "./modal/InsertModal"
import ListModal from "../pugjjig/modal/ListModal"

class PugjjigProvider extends Component {
    render() {

        // Props Init
        const {
            closeModal,
            insertModalState,
            listModalState,
            stoId,
            stoName,
            stoAddress,
            stoUrl
        } = this.props;

        return (
            <Fragment>
                <InsertModal
                    closeModal={closeModal}
                    modalState={insertModalState}
                    stoId={stoId}
                    stoName={stoName}
                    stoAddress={stoAddress}
                    stoUrl={stoUrl}
                />
                <ListModal
                    keyword={stoName}
                    modalState={listModalState}
                    closeModal={closeModal}
                />
            </Fragment>
        )
    }
}

export default PugjjigProvider;