import React from 'react';

function Pagination({ currentPage, totalPages, onPageChange }) {
    return (
        <div>
            <button
                onClick={() => onPageChange(currentPage - 1)}
                disabled={currentPage === 0}
            >
                Previous
            </button>
            <span> Page {currentPage + 1} of {totalPages} </span>
            <button
                onClick={() => onPageChange(currentPage + 1)}
                disabled={currentPage >= totalPages - 1}
            >
                Next
            </button>
        </div>
    );
}

export default Pagination;
