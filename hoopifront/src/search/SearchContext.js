import React, { createContext, useState, useContext } from "react";

const SearchContext = createContext();

export const useSearch = () => useContext(SearchContext);

export const SearchProvider = ({ children }) => {
    const [keyword, setKeyword] = useState('');
    const [searchCate, setSearchCate] = useState('');

    const value = {
        keyword,
        setKeyword,
        searchCate,
        setSearchCate
    };

    return (
        <SearchContext.Provider value={value}>
            {children}
        </SearchContext.Provider>
    );
};
