import React, {Component} from "react"
import {NormalHeaderWrap} from "../style"
import {Link} from "react-router-dom"

class NormalHeader extends Component {
    render() {
        return (
            <NormalHeaderWrap>
                <Link to="/">푹찍</Link>
            </NormalHeaderWrap>
        )
    }
}

export default NormalHeader;