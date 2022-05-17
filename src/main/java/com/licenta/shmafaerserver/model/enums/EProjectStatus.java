package com.licenta.shmafaerserver.model.enums;

public enum EProjectStatus {
    //local status
    SAVED, //locally
    APPROVED, //approved by coordinator

    //status from SH
    PENDING, //sent request to SH
    SUCCEEDED, //archived by SH
    FAILED, //attempted but failed by SH
}
