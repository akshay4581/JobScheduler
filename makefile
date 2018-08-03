JFLAGS = -g
JC = javac
.SUFFIXES: .java .class
.java.class:
	$(JC) $(JFLAGS) $*.java

CLASSES = \
	MinHeapImpl.java \
	Job.java \
	jobscheduler.java \
	RBTImpl.java \
	Scheduler.java

default: classes

classes: $(CLASSES:.java=.class)

clean:
	$(RM) *.class