import DaumPostcode from 'react-daum-postcode';
import { useState } from 'react';

const DaumPostCode = ({ onComplete }) => {

    const handleComplete = (data) => {
        const fullAddress = data.address;
        const extraAddress = data.bname !== '' ? data.bname : '';

        onComplete({
            address: fullAddress,
            extraAddress: extraAddress,
            zoneCode: data.zonecode
        });
    };

    return (
        <DaumPostcode
            onComplete={handleComplete}
        />
    );
};

export default DaumPostCode;
