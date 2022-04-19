package com.licenta.shmafaerserver.model.enums;

public enum EProjectStatus {
    //local status
    SAVED, //locally
    APPROVED, //approved by coordinator/admin

    //status from SH
    PENDING, //sent request to SH
    SCHEDULED, //scheduled for archiving by SH
    REJECTED, //rejected by SH
    SUCCEEDED, //archived by SH
    FAILED //attempted but failed by SH

}
