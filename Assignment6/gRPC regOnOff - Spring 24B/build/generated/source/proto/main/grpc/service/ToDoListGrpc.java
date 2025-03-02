package service;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.49.1)",
    comments = "Source: services/todo.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class ToDoListGrpc {

  private ToDoListGrpc() {}

  public static final String SERVICE_NAME = "services.ToDoList";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<service.TaskRequest,
      service.TaskResponse> getAddTaskMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "AddTask",
      requestType = service.TaskRequest.class,
      responseType = service.TaskResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<service.TaskRequest,
      service.TaskResponse> getAddTaskMethod() {
    io.grpc.MethodDescriptor<service.TaskRequest, service.TaskResponse> getAddTaskMethod;
    if ((getAddTaskMethod = ToDoListGrpc.getAddTaskMethod) == null) {
      synchronized (ToDoListGrpc.class) {
        if ((getAddTaskMethod = ToDoListGrpc.getAddTaskMethod) == null) {
          ToDoListGrpc.getAddTaskMethod = getAddTaskMethod =
              io.grpc.MethodDescriptor.<service.TaskRequest, service.TaskResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "AddTask"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  service.TaskRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  service.TaskResponse.getDefaultInstance()))
              .setSchemaDescriptor(new ToDoListMethodDescriptorSupplier("AddTask"))
              .build();
        }
      }
    }
    return getAddTaskMethod;
  }

  private static volatile io.grpc.MethodDescriptor<service.UserRequest,
      service.TaskListResponse> getGetTasksMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetTasks",
      requestType = service.UserRequest.class,
      responseType = service.TaskListResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<service.UserRequest,
      service.TaskListResponse> getGetTasksMethod() {
    io.grpc.MethodDescriptor<service.UserRequest, service.TaskListResponse> getGetTasksMethod;
    if ((getGetTasksMethod = ToDoListGrpc.getGetTasksMethod) == null) {
      synchronized (ToDoListGrpc.class) {
        if ((getGetTasksMethod = ToDoListGrpc.getGetTasksMethod) == null) {
          ToDoListGrpc.getGetTasksMethod = getGetTasksMethod =
              io.grpc.MethodDescriptor.<service.UserRequest, service.TaskListResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetTasks"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  service.UserRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  service.TaskListResponse.getDefaultInstance()))
              .setSchemaDescriptor(new ToDoListMethodDescriptorSupplier("GetTasks"))
              .build();
        }
      }
    }
    return getGetTasksMethod;
  }

  private static volatile io.grpc.MethodDescriptor<service.TaskUpdateRequest,
      service.TaskResponse> getCompleteTaskMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "CompleteTask",
      requestType = service.TaskUpdateRequest.class,
      responseType = service.TaskResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<service.TaskUpdateRequest,
      service.TaskResponse> getCompleteTaskMethod() {
    io.grpc.MethodDescriptor<service.TaskUpdateRequest, service.TaskResponse> getCompleteTaskMethod;
    if ((getCompleteTaskMethod = ToDoListGrpc.getCompleteTaskMethod) == null) {
      synchronized (ToDoListGrpc.class) {
        if ((getCompleteTaskMethod = ToDoListGrpc.getCompleteTaskMethod) == null) {
          ToDoListGrpc.getCompleteTaskMethod = getCompleteTaskMethod =
              io.grpc.MethodDescriptor.<service.TaskUpdateRequest, service.TaskResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "CompleteTask"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  service.TaskUpdateRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  service.TaskResponse.getDefaultInstance()))
              .setSchemaDescriptor(new ToDoListMethodDescriptorSupplier("CompleteTask"))
              .build();
        }
      }
    }
    return getCompleteTaskMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static ToDoListStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<ToDoListStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<ToDoListStub>() {
        @java.lang.Override
        public ToDoListStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new ToDoListStub(channel, callOptions);
        }
      };
    return ToDoListStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static ToDoListBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<ToDoListBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<ToDoListBlockingStub>() {
        @java.lang.Override
        public ToDoListBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new ToDoListBlockingStub(channel, callOptions);
        }
      };
    return ToDoListBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static ToDoListFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<ToDoListFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<ToDoListFutureStub>() {
        @java.lang.Override
        public ToDoListFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new ToDoListFutureStub(channel, callOptions);
        }
      };
    return ToDoListFutureStub.newStub(factory, channel);
  }

  /**
   */
  public static abstract class ToDoListImplBase implements io.grpc.BindableService {

    /**
     */
    public void addTask(service.TaskRequest request,
        io.grpc.stub.StreamObserver<service.TaskResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getAddTaskMethod(), responseObserver);
    }

    /**
     */
    public void getTasks(service.UserRequest request,
        io.grpc.stub.StreamObserver<service.TaskListResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetTasksMethod(), responseObserver);
    }

    /**
     */
    public void completeTask(service.TaskUpdateRequest request,
        io.grpc.stub.StreamObserver<service.TaskResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getCompleteTaskMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getAddTaskMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                service.TaskRequest,
                service.TaskResponse>(
                  this, METHODID_ADD_TASK)))
          .addMethod(
            getGetTasksMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                service.UserRequest,
                service.TaskListResponse>(
                  this, METHODID_GET_TASKS)))
          .addMethod(
            getCompleteTaskMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                service.TaskUpdateRequest,
                service.TaskResponse>(
                  this, METHODID_COMPLETE_TASK)))
          .build();
    }
  }

  /**
   */
  public static final class ToDoListStub extends io.grpc.stub.AbstractAsyncStub<ToDoListStub> {
    private ToDoListStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ToDoListStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new ToDoListStub(channel, callOptions);
    }

    /**
     */
    public void addTask(service.TaskRequest request,
        io.grpc.stub.StreamObserver<service.TaskResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getAddTaskMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void getTasks(service.UserRequest request,
        io.grpc.stub.StreamObserver<service.TaskListResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetTasksMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void completeTask(service.TaskUpdateRequest request,
        io.grpc.stub.StreamObserver<service.TaskResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getCompleteTaskMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class ToDoListBlockingStub extends io.grpc.stub.AbstractBlockingStub<ToDoListBlockingStub> {
    private ToDoListBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ToDoListBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new ToDoListBlockingStub(channel, callOptions);
    }

    /**
     */
    public service.TaskResponse addTask(service.TaskRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getAddTaskMethod(), getCallOptions(), request);
    }

    /**
     */
    public service.TaskListResponse getTasks(service.UserRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetTasksMethod(), getCallOptions(), request);
    }

    /**
     */
    public service.TaskResponse completeTask(service.TaskUpdateRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getCompleteTaskMethod(), getCallOptions(), request);
    }
  }

  /**
   */
  public static final class ToDoListFutureStub extends io.grpc.stub.AbstractFutureStub<ToDoListFutureStub> {
    private ToDoListFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ToDoListFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new ToDoListFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<service.TaskResponse> addTask(
        service.TaskRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getAddTaskMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<service.TaskListResponse> getTasks(
        service.UserRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetTasksMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<service.TaskResponse> completeTask(
        service.TaskUpdateRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getCompleteTaskMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_ADD_TASK = 0;
  private static final int METHODID_GET_TASKS = 1;
  private static final int METHODID_COMPLETE_TASK = 2;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final ToDoListImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(ToDoListImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_ADD_TASK:
          serviceImpl.addTask((service.TaskRequest) request,
              (io.grpc.stub.StreamObserver<service.TaskResponse>) responseObserver);
          break;
        case METHODID_GET_TASKS:
          serviceImpl.getTasks((service.UserRequest) request,
              (io.grpc.stub.StreamObserver<service.TaskListResponse>) responseObserver);
          break;
        case METHODID_COMPLETE_TASK:
          serviceImpl.completeTask((service.TaskUpdateRequest) request,
              (io.grpc.stub.StreamObserver<service.TaskResponse>) responseObserver);
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

  private static abstract class ToDoListBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    ToDoListBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return service.ToDoListProto.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("ToDoList");
    }
  }

  private static final class ToDoListFileDescriptorSupplier
      extends ToDoListBaseDescriptorSupplier {
    ToDoListFileDescriptorSupplier() {}
  }

  private static final class ToDoListMethodDescriptorSupplier
      extends ToDoListBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    ToDoListMethodDescriptorSupplier(String methodName) {
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
      synchronized (ToDoListGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new ToDoListFileDescriptorSupplier())
              .addMethod(getAddTaskMethod())
              .addMethod(getGetTasksMethod())
              .addMethod(getCompleteTaskMethod())
              .build();
        }
      }
    }
    return result;
  }
}
