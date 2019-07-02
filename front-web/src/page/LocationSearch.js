import React from 'react';
import * as CallApiGet from '../util/CallApiGet';
import {BootstrapTable,
    TableHeaderColumn} from 'react-bootstrap-table';
import '../../node_modules/react-bootstrap-table/css/react-bootstrap-table.css'

class LocationSearch extends React.Component {
    constructor(props) {
        super(props);
        this.state = {value: '', data: [], total_count: '', page_count: '', page_component: [], is_end: false};
    }
    handleChange = (event) => {
        this.setState({value: event.target.value});
    }

    callThisPage = (item) => (event) => {
        CallApiGet.getLocationList(this, this.state.value, item);
    }

    handleSubmit = (event) => {
        CallApiGet.getLocationList(this, this.state.value, 1);

        setTimeout(() => {
            let pageCount;
            if(this.state.total_count > 10){
                pageCount = Math.ceil(this.state.total_count / 15);
            } else {
                pageCount = 1;
            }

            if(pageCount > 45){
                pageCount = 45;
            }

            this.setState({page_count: pageCount});

            let pageButton = [];
            for (let i = 1; i <= pageCount; i++) {
                pageButton.push(<button onClick={this.callThisPage(i)}> {i} </button>);
            }

            this.setState({page_component: pageButton})

        }, 500);

        event.preventDefault();
    }

    render() {

        return (
            <div>
                [ 장소 검색 ]
                <form onSubmit={this.handleSubmit}>
                    <label>
                        <input type="text" value={this.state.value} onChange={this.handleChange} />
                    </label>
                    <input type="submit" value="검색하기" />
                </form>

                {this.state.data.map((item, num) => {
                    return (<LocationSearchResult
                        address_name={item.address_name}
                        category_name={item.category_name}
                        phone={item.phone}
                        place_name={item.place_name}
                        place_url={item.place_url}
                        road_address_name={item.road_address_name}
                        id={item.id}
                        key={num}/>);
                })}


                {this.state.page_component}

                {<div><KeywordHistory></KeywordHistory></div>}
                {<div><PopulateKeyword></PopulateKeyword></div>}
            </div>
        );
    }
}

class LocationSearchResult extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            showPopup: false
        };
    }
    togglePopup() {
        this.setState({
            showPopup: !this.state.showPopup
        });
    }

    render() {
        return(
            <div>
                <li><button onClick={this.togglePopup.bind(this)}>{this.props.place_name}</button></li>
                {this.state.showPopup ?
                    <Popup
                        addressName={this.props.address_name}
                        categoryName={this.props.category_name}
                        phone={this.props.phone}
                        placeName={this.props.place_name}
                        placeUrl={this.props.place_url}
                        roadAddressName={this.props.road_address_name}
                        id={this.props.id}
                        closePopup={this.togglePopup.bind(this)}
                    />
                    : null
                }
            </div>
        );
    }
}

class Popup extends React.Component {
    render() {
        return (
            <div className='popup'>
                <div className='popup_inner'>
                    <h2>장소(업체)명 : {this.props.placeName}</h2>
                    <h3>카테고리 : {this.props.categoryName}</h3>
                    <h3>전화번호 : {this.props.phone}</h3>
                    <h3>상세페이지 : <a href={`${this.props.placeUrl}`} target="_blank">확인하기(클릭)</a></h3>
                    <h3>지번주소 : {this.props.addressName}</h3>
                    <h3>도로명주소 : {this.props.roadAddressName}</h3>
                    <h2>지도보기 : <a href={`https://map.kakao.com/link/to/${this.props.id}`} target="_blank">바로가기(클릭)</a></h2>
                    <button onClick={this.props.closePopup}>클릭 시 축소</button>
                </div>
            </div>
        );
    }
}


class KeywordHistory extends React.Component {
    constructor(props) {
        super(props);
        this.state = {value: '', data: []};
        CallApiGet.getMyKeywordHistory(this);
    }
    handleChange = (event) => {
        this.setState({value: event.target.value});
    }

    handleRefresh = (event) => {
        CallApiGet.getMyKeywordHistory(this);
    }

    render() {
        return(
            <div>
                [ 키워드 히스토리 ]
                <span><button onClick={this.handleRefresh}> 새로고침 </button></span>
                <KeywordHistoryResult data={this.state.data}/>

            </div>
        );
    }
}

class KeywordHistoryResult extends React.Component {
    render() {
        return (
            <div>
                <BootstrapTable data={this.props.data}>
                    <TableHeaderColumn isKey dataField='keyword'>
                        검색키워드
                    </TableHeaderColumn>
                    <TableHeaderColumn dataField='lastSearchDate'>
                        최근조회일
                    </TableHeaderColumn>
                </BootstrapTable>
            </div>
        );
    }

}

class PopulateKeyword extends React.Component {
    constructor(props) {
        super(props);
        this.state = {value: '', data: []};
        CallApiGet.getPopulateKeyword(this);
    }
    handleChange = (event) => {
        this.setState({value: event.target.value});
    }

    handleRefresh = (event) => {
        CallApiGet.getPopulateKeyword(this);
    }

    handleSubmit = (event) => {

        event.preventDefault();
    }

    render() {
        return(
            <div>
                [ 인기 검색어 TOP 10 ]
                <span><button onClick={this.handleRefresh}> 새로고침 </button></span>
                <PopulateKeywordResult data={this.state.data}/>
            </div>
        );
    }
}

class PopulateKeywordResult extends React.Component {
    render() {
        return (
        <div>
            <BootstrapTable data={this.props.data}>
                <TableHeaderColumn isKey dataField='keyword'>
                    인기키워드
                </TableHeaderColumn>
                <TableHeaderColumn dataField='count'>
                    조회수
                </TableHeaderColumn>
            </BootstrapTable>
        </div>
        );
    }
}

export default LocationSearch;