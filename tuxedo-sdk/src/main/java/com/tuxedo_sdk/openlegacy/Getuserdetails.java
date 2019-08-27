package com.tuxedo_sdk.openlegacy;

import java.math.BigInteger;
import java.math.BigDecimal;
import java.util.List;
import java.util.Date;
import org.openlegacy.core.annotations.common.*;
import org.openlegacy.core.annotations.rpc.*;
import org.openlegacy.core.FieldType.*;
import org.openlegacy.core.rpc.actions.RpcActions.EXECUTE;


@RpcEntity(name="Getuserdetails", displayName="Getuserdetails", language=Languages.JOLT)
@RpcActions(actions = {
        @Action(action = EXECUTE.class, path = "GETUSERDETAILS", displayName = "Execute", alias = "execute" )            })
public class Getuserdetails {

    @RpcField(direction = Direction.INPUT_OUTPUT, originalName = "ID3", legacyType = "INTEGER")
    @RpcNumericField(minimumValue = -2147483648, maximumValue = 2147483647, decimalPlaces = 0)
    private Integer id3;

    @RpcField(direction = Direction.OUTPUT, originalName = "ADDRESS3", legacyType = "STRING")
    private String address3;

    @RpcField(direction = Direction.OUTPUT, originalName = "NAME3", legacyType = "CARRAY")
    private String name3;

    @RpcField(direction = Direction.OUTPUT, originalName = "AGE3", legacyType = "FLOAT")
    @RpcNumericField(minimumValue = -340282346638528860000000000000000000000f, maximumValue = 340282346638528860000000000000000000000f, decimalPlaces = 0)
    private Float age3;

    @RpcField(direction = Direction.OUTPUT, originalName = "CURRENCY3", legacyType = "DOUBLE")
    @RpcNumericField(minimumValue = -340282346638528860000000000000000000000D, maximumValue = 340282346638528860000000000000000000000D, decimalPlaces = 0)
    private Double currency3;

    @RpcField(direction = Direction.OUTPUT, originalName = "YEAR3", legacyType = "SHORT")
    @RpcNumericField(minimumValue = -32768, maximumValue = 32767, decimalPlaces = 0)
    private Short year3;

    @RpcField(direction = Direction.OUTPUT, originalName = "CHILDREN3", legacyType = "BYTE")
    @RpcNumericField(minimumValue = -128, maximumValue = 127, decimalPlaces = 0)
    private Byte children3;
}

