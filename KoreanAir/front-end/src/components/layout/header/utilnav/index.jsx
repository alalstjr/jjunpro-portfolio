import React, { Component } from 'react'
import { connect } from "react-redux";
import PropTypes from "prop-types"

import { changeLocale } from "../../../../connectedIntlProvider/action";

import { Utilnav, LangLink } from "../style";
import { Container, LeftBox, RightBox, ListUl } from "../../../../style/globalStyles";

import UserModal from "../../../user";

class Nav extends Component {

    langChange = (e) => {
        const { dispatch, locale } = this.props;
        const nextLocale = locale.locale === "ko" ? "en" : "ko";

        e.preventDefault();
        dispatch(changeLocale(nextLocale));
    }

    render() {

        // props Init
        const { 
            locale, 
            intl,
            account
        } = this.props;

        // Variables Init
        let loginContainer;
        let singUpContainer;

        const loginComponent = () => {
            return account.token ?
                <UserModal  
                    text = {intl.formatMessage({ id: "HEADER.UTILNAV.logout" })}
                    req = {"logout"}
                />
                :
                <UserModal
                    text = {intl.formatMessage({ id: "HEADER.UTILNAV.login" })}
                    req = {"login"}
                /> 
                ;
        }

        const singUpComponent = () => {
            return account.token ?
                null
                :
                <li>
                    <UserModal
                        text = {intl.formatMessage({ id: "HEADER.UTILNAV.singUp" })}
                        req = {"singUp"}
                    />
                </li>
                ;
        }

        loginContainer = loginComponent();
        singUpContainer = singUpComponent();

        return (
            <Utilnav>
                <Container>
                    <LeftBox>
                        언어
                        <LangLink
                            href="javscript:;"
                            onClick={this.langChange}
                        >
                            {locale.locale === "ko" ? "English" : "한국어"}
                        </LangLink>
                    </LeftBox>
                    <RightBox>
                        <ListUl>
                            <li>{loginContainer}</li>
                            {singUpContainer}
                            <li>{intl.formatMessage({ id: "HEADER.UTILNAV.reservation" })}</li>
                            <li>{intl.formatMessage({ id: "HEADER.UTILNAV.wishList" })}</li>
                            <li>{intl.formatMessage({ id: "HEADER.UTILNAV.search" })}</li>
                        </ListUl>
                    </RightBox>
                </Container>
            </Utilnav>
        )
    }
}

Nav.propTypes = {
    locale: PropTypes.object.isRequired,
    dispatch: PropTypes.func.isRequired,
    intl: PropTypes.object.isRequired
}

const mapStateToProps = state => {
    return {
        locale: state.locale
    };
};

export default connect(mapStateToProps)(Nav);