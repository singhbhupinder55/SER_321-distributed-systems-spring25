package service;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.49.1)",
    comments = "Source: services/qanda.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class QandaGrpc {

  private QandaGrpc() {}

  public static final String SERVICE_NAME = "services.Qanda";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<service.QuestionReq,
      service.QuestionRes> getAddQuestionMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "addQuestion",
      requestType = service.QuestionReq.class,
      responseType = service.QuestionRes.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<service.QuestionReq,
      service.QuestionRes> getAddQuestionMethod() {
    io.grpc.MethodDescriptor<service.QuestionReq, service.QuestionRes> getAddQuestionMethod;
    if ((getAddQuestionMethod = QandaGrpc.getAddQuestionMethod) == null) {
      synchronized (QandaGrpc.class) {
        if ((getAddQuestionMethod = QandaGrpc.getAddQuestionMethod) == null) {
          QandaGrpc.getAddQuestionMethod = getAddQuestionMethod =
              io.grpc.MethodDescriptor.<service.QuestionReq, service.QuestionRes>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "addQuestion"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  service.QuestionReq.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  service.QuestionRes.getDefaultInstance()))
              .setSchemaDescriptor(new QandaMethodDescriptorSupplier("addQuestion"))
              .build();
        }
      }
    }
    return getAddQuestionMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.google.protobuf.Empty,
      service.AllQuestionsRes> getViewQuestionsMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "viewQuestions",
      requestType = com.google.protobuf.Empty.class,
      responseType = service.AllQuestionsRes.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.google.protobuf.Empty,
      service.AllQuestionsRes> getViewQuestionsMethod() {
    io.grpc.MethodDescriptor<com.google.protobuf.Empty, service.AllQuestionsRes> getViewQuestionsMethod;
    if ((getViewQuestionsMethod = QandaGrpc.getViewQuestionsMethod) == null) {
      synchronized (QandaGrpc.class) {
        if ((getViewQuestionsMethod = QandaGrpc.getViewQuestionsMethod) == null) {
          QandaGrpc.getViewQuestionsMethod = getViewQuestionsMethod =
              io.grpc.MethodDescriptor.<com.google.protobuf.Empty, service.AllQuestionsRes>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "viewQuestions"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.google.protobuf.Empty.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  service.AllQuestionsRes.getDefaultInstance()))
              .setSchemaDescriptor(new QandaMethodDescriptorSupplier("viewQuestions"))
              .build();
        }
      }
    }
    return getViewQuestionsMethod;
  }

  private static volatile io.grpc.MethodDescriptor<service.AnswerReq,
      service.AnswerRes> getAddAnswerMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "addAnswer",
      requestType = service.AnswerReq.class,
      responseType = service.AnswerRes.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<service.AnswerReq,
      service.AnswerRes> getAddAnswerMethod() {
    io.grpc.MethodDescriptor<service.AnswerReq, service.AnswerRes> getAddAnswerMethod;
    if ((getAddAnswerMethod = QandaGrpc.getAddAnswerMethod) == null) {
      synchronized (QandaGrpc.class) {
        if ((getAddAnswerMethod = QandaGrpc.getAddAnswerMethod) == null) {
          QandaGrpc.getAddAnswerMethod = getAddAnswerMethod =
              io.grpc.MethodDescriptor.<service.AnswerReq, service.AnswerRes>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "addAnswer"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  service.AnswerReq.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  service.AnswerRes.getDefaultInstance()))
              .setSchemaDescriptor(new QandaMethodDescriptorSupplier("addAnswer"))
              .build();
        }
      }
    }
    return getAddAnswerMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static QandaStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<QandaStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<QandaStub>() {
        @java.lang.Override
        public QandaStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new QandaStub(channel, callOptions);
        }
      };
    return QandaStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static QandaBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<QandaBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<QandaBlockingStub>() {
        @java.lang.Override
        public QandaBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new QandaBlockingStub(channel, callOptions);
        }
      };
    return QandaBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static QandaFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<QandaFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<QandaFutureStub>() {
        @java.lang.Override
        public QandaFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new QandaFutureStub(channel, callOptions);
        }
      };
    return QandaFutureStub.newStub(factory, channel);
  }

  /**
   */
  public static abstract class QandaImplBase implements io.grpc.BindableService {

    /**
     */
    public void addQuestion(service.QuestionReq request,
        io.grpc.stub.StreamObserver<service.QuestionRes> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getAddQuestionMethod(), responseObserver);
    }

    /**
     */
    public void viewQuestions(com.google.protobuf.Empty request,
        io.grpc.stub.StreamObserver<service.AllQuestionsRes> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getViewQuestionsMethod(), responseObserver);
    }

    /**
     */
    public void addAnswer(service.AnswerReq request,
        io.grpc.stub.StreamObserver<service.AnswerRes> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getAddAnswerMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getAddQuestionMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                service.QuestionReq,
                service.QuestionRes>(
                  this, METHODID_ADD_QUESTION)))
          .addMethod(
            getViewQuestionsMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                com.google.protobuf.Empty,
                service.AllQuestionsRes>(
                  this, METHODID_VIEW_QUESTIONS)))
          .addMethod(
            getAddAnswerMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                service.AnswerReq,
                service.AnswerRes>(
                  this, METHODID_ADD_ANSWER)))
          .build();
    }
  }

  /**
   */
  public static final class QandaStub extends io.grpc.stub.AbstractAsyncStub<QandaStub> {
    private QandaStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected QandaStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new QandaStub(channel, callOptions);
    }

    /**
     */
    public void addQuestion(service.QuestionReq request,
        io.grpc.stub.StreamObserver<service.QuestionRes> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getAddQuestionMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void viewQuestions(com.google.protobuf.Empty request,
        io.grpc.stub.StreamObserver<service.AllQuestionsRes> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getViewQuestionsMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void addAnswer(service.AnswerReq request,
        io.grpc.stub.StreamObserver<service.AnswerRes> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getAddAnswerMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class QandaBlockingStub extends io.grpc.stub.AbstractBlockingStub<QandaBlockingStub> {
    private QandaBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected QandaBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new QandaBlockingStub(channel, callOptions);
    }

    /**
     */
    public service.QuestionRes addQuestion(service.QuestionReq request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getAddQuestionMethod(), getCallOptions(), request);
    }

    /**
     */
    public service.AllQuestionsRes viewQuestions(com.google.protobuf.Empty request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getViewQuestionsMethod(), getCallOptions(), request);
    }

    /**
     */
    public service.AnswerRes addAnswer(service.AnswerReq request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getAddAnswerMethod(), getCallOptions(), request);
    }
  }

  /**
   */
  public static final class QandaFutureStub extends io.grpc.stub.AbstractFutureStub<QandaFutureStub> {
    private QandaFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected QandaFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new QandaFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<service.QuestionRes> addQuestion(
        service.QuestionReq request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getAddQuestionMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<service.AllQuestionsRes> viewQuestions(
        com.google.protobuf.Empty request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getViewQuestionsMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<service.AnswerRes> addAnswer(
        service.AnswerReq request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getAddAnswerMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_ADD_QUESTION = 0;
  private static final int METHODID_VIEW_QUESTIONS = 1;
  private static final int METHODID_ADD_ANSWER = 2;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final QandaImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(QandaImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_ADD_QUESTION:
          serviceImpl.addQuestion((service.QuestionReq) request,
              (io.grpc.stub.StreamObserver<service.QuestionRes>) responseObserver);
          break;
        case METHODID_VIEW_QUESTIONS:
          serviceImpl.viewQuestions((com.google.protobuf.Empty) request,
              (io.grpc.stub.StreamObserver<service.AllQuestionsRes>) responseObserver);
          break;
        case METHODID_ADD_ANSWER:
          serviceImpl.addAnswer((service.AnswerReq) request,
              (io.grpc.stub.StreamObserver<service.AnswerRes>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        default:
          throw new AssertionError();
      }
    }
  }

  private static abstract class QandaBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    QandaBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return service.QandAProto.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("Qanda");
    }
  }

  private static final class QandaFileDescriptorSupplier
      extends QandaBaseDescriptorSupplier {
    QandaFileDescriptorSupplier() {}
  }

  private static final class QandaMethodDescriptorSupplier
      extends QandaBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    QandaMethodDescriptorSupplier(String methodName) {
      this.methodName = methodName;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.MethodDescriptor getMethodDescriptor() {
      return getServiceDescriptor().findMethodByName(methodName);
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (QandaGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new QandaFileDescriptorSupplier())
              .addMethod(getAddQuestionMethod())
              .addMethod(getViewQuestionsMethod())
              .addMethod(getAddAnswerMethod())
              .build();
        }
      }
    }
    return result;
  }
}
