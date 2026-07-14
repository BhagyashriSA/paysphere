package com.test.cph.serviceinf;

import com.test.cph.dto.FundTransferRequestDTO;
import com.test.cph.dto.FundTransferResponseDTO;

public interface FundTransferService {

    public FundTransferResponseDTO fundTransfer(FundTransferRequestDTO request);
}
