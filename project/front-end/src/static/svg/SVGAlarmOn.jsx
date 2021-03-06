import React from "react";

class SVGAlarmOn extends React.Component {
    render() {
        return (
            <svg
                x="0px" y="0px"
                width={this.props.width ? this.props.width : "auto"}
                height={this.props.height ? this.props.height : "auto"}
                viewBox={"0 0 64 64"}>
                <g id="notification">
                    <path fill={this.props.color} d="M27,60a7,7,0,0,0,6.92-6H20.08A7,7,0,0,0,27,60Z"/>
                    <path fill={this.props.color}
                          d="M46,9a1.76,1.76,0,0,0-1.754,1.906l.585,7.021A1.177,1.177,0,0,0,46,19H46a1.177,1.177,0,0,0,1.166-1.073l.585-7.021A1.76,1.76,0,0,0,46,9Z"/>
                    <circle cx="46" cy="26" r="1"/>
                    <path fill={this.props.color}
                          d="M46,4A14.015,14.015,0,0,0,32,18a13.868,13.868,0,0,0,2.72,8.27.982.982,0,0,1,.18.81l-.52,2.34,1.84-.7a1.073,1.073,0,0,1,.35-.06,1.026,1.026,0,0,1,.63.22A13.838,13.838,0,0,0,46,32,14,14,0,0,0,46,4Zm0,25a3,3,0,1,1,3-3A3,3,0,0,1,46,29Zm3.163-10.907A3.19,3.19,0,0,1,46,21H46a3.19,3.19,0,0,1-3.16-2.907l-.585-7.021a3.761,3.761,0,1,1,7.5,0Z"/>
                    <path fill={this.props.color}
                          d="M31.81,10.63c.07-.12.12-.24.19-.36V9A5,5,0,0,0,22,9v1.69a18.566,18.566,0,0,1,9.81-.06Z"/>
                    <path fill={this.props.color}
                          d="M44,47V33.86a16.052,16.052,0,0,1-7.6-3.07l-3.05,1.15a1.008,1.008,0,0,1-1.33-1.16l.83-3.7A15.81,15.81,0,0,1,30,18a16.036,16.036,0,0,1,1-5.52A17.237,17.237,0,0,0,27,12,17.024,17.024,0,0,0,10,29V47a1.033,1.033,0,0,1-.29.71L5.41,52H48.59l-4.3-4.29A1.033,1.033,0,0,1,44,47Z"/>
                </g>
            </svg>
        );
    }
}

export default SVGAlarmOn;