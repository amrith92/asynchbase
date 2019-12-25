package org.hbase.async;

import org.hbase.async.generated.ClientPB;

final class RowWriteRequestUtils {

  private RowWriteRequestUtils() {
    throw new IllegalStateException("Cannot instantiate instance of RowWriteRequestUtils!");
  }

  static int qualifierValueSize(final byte[] key, final int family, final byte[][] families, final byte[][][] qualifiers, final byte[][][] values) {
    int size = 0;
    for (int j = 0; j < qualifiers[family].length; j++) {
      size += KeyValue.predictSerializedSize(key, families[family], qualifiers[family][j], values[family][j]);
    }
    return size;
  }

  static ClientPB.MutationProto.ColumnValue.Builder columnsFor(final byte[][] families, final byte[][][] qualifiers, final byte[][][] values, final long timestamp) {
    final ClientPB.MutationProto.ColumnValue.Builder columns =  // All columns ...
        ClientPB.MutationProto.ColumnValue.newBuilder();

    for (int family = 0; family < families.length; family++) {
      columns.clear();
      columns.setFamily(Bytes.wrap(families[family]));
      // Now add all the qualifier-value pairs.
      for (int i = 0; i < qualifiers[family].length; i++) {
        final ClientPB.MutationProto.ColumnValue.QualifierValue column =
            ClientPB.MutationProto.ColumnValue.QualifierValue.newBuilder()
                .setQualifier(Bytes.wrap(qualifiers[family][i]))
                .setValue(Bytes.wrap(values[family][i]))
                .setTimestamp(timestamp)
                .build();
        columns.addQualifierValue(column);
      }
    }

    return columns;
  }
}
