################################################################################
#  Licensed to the Apache Software Foundation (ASF) under one
#  or more contributor license agreements.  See the NOTICE file
#  distributed with this work for additional information
#  regarding copyright ownership.  The ASF licenses this file
#  to you under the Apache License, Version 2.0 (the
#  "License"); you may not use this file except in compliance
#  with the License.  You may obtain a copy of the License at
#
#      http://www.apache.org/licenses/LICENSE-2.0
#
#  Unless required by applicable law or agreed to in writing, software
#  distributed under the License is distributed on an "AS IS" BASIS,
#  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
#  See the License for the specific language governing permissions and
# limitations under the License.
################################################################################

FROM java:8-jre-alpine

# Karaf environment variables
ENV KARAF_INSTALL_PATH=/opt
ENV KARAF_HOME $KARAF_INSTALL_PATH/apache-karaf
ENV PATH $PATH:$KARAF_HOME/bin

# karaf_dist can point to a directory or a tarball on the local system
ARG karaf_dist=NOT_SET

# Install build dependencies and karaf
ADD $karaf_dist $KARAF_INSTALL_PATH
RUN set -x && \
  ln -s $KARAF_INSTALL_PATH/apache-karaf* $KARAF_HOME

COPY docker-entrypoint.sh /
COPY feature.xml ${KARAF_HOME}/deploy
COPY raspo-kafka-core/generated/raspo-kafka-core.jar ${KARAF_HOME}/deploy

EXPOSE 8101 1099 44444 8181
ENTRYPOINT ["/docker-entrypoint.sh"]
