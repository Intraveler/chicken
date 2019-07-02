import React from 'react';
import * as CallApiGet from '../util/CallApiGet';

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

        }, 200);

        event.preventDefault();
    }

    render() {

        return (
            <div>
                -- 장소 검색 --
                <form onSubmit={this.handleSubmit}>
                    <label>
                        장소 : <input type="text" value={this.state.value} onChange={this.handleChange} />
                    </label>
                    <input type="submit" value="Submit" />
                </form>

                {this.state.data.map((item, num) => {
                    return (<LocationSearchResult
                        address_name={item.address_name}
                        category_name={item.category_name}
                        phone={item.phone}
                        place_name={item.place_name}
                        place_url={item.place_url}
                        road_address_name={item.road_address_name}
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
                    <h3>상세페이지 : {this.props.placeUrl}</h3>
                    <h3>지번주소 : {this.props.addressName}</h3>
                    <h3>도로명주소 : {this.props.roadAddressName}</h3>
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
    }
    handleChange = (event) => {
        this.setState({value: event.target.value});
    }

    handleClear = (event) => {
        this.setState({data: []});
    }

    handleSubmit = (event) => {
        CallApiGet.getMyKeywordHistory(this);
        event.preventDefault();
    }
    render() {
        return(
            <div>
                -- 키워드 히스토리 --
                <form onSubmit={this.handleSubmit}>
                    내 키워드 내역 확인 <input type="submit" value="확인하기" />
                </form>
                <button onClick={this.handleClear}> 접기 </button>

                {this.state.data.map((item, num) => {
                    return (<KeywordHistoryResult
                        keyword={item.keyword}
                        lastSearchDate={item.lastSearchDate}
                        key={num}/>);
                })}
            </div>
        );
    }
}

class KeywordHistoryResult extends React.Component {
    render() {
        return(
            <li>{this.props.keyword} {this.props.lastSearchDate}</li>
        );
    }
}

class PopulateKeyword extends React.Component {
    constructor(props) {
        super(props);
        this.state = {value: '', data: []};
    }
    handleChange = (event) => {
        this.setState({value: event.target.value});
    }

    handleClear = (event) => {
        this.setState({data: []});
    }

    handleSubmit = (event) => {
        CallApiGet.getPopulateKeyword(this);
        event.preventDefault();
    }
    render() {
        return(
            <div>
                -- 인기 검색어 TOP 10 --
                <form onSubmit={this.handleSubmit}>
                    인기 검색어 확인 <input type="submit" value="Submit" />
                </form>
                <button onClick={this.handleClear}> 접기 </button>
                {this.state.data.map((item, num) => {
                    return (<PopulateKeywordResult
                        keyword={item.keyword}
                        count={item.count}
                        key={num}/>);
                })}
            </div>
        );
    }
}

class PopulateKeywordResult extends React.Component {
    render() {
        return(
            <li>{this.props.keyword} {this.props.count}</li>
        );
    }
}

export default LocationSearch;